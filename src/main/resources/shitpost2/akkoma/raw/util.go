package raw

import (
    "net/http"
    "log/slog"
    "bytes"
    "net/url"
    "errors"
    "fmt"
    "io"
    "encoding/json"
)

func checkForRateLimit(resp *http.Response) (string, error) {
    if resp.StatusCode == http.StatusTooManyRequests {
        remainingRequests := resp.Header.Get("X-RateLimit-Remaining")
        totalQuota := resp.Header.Get("X-RateLimit-Limit")
        resetTime := resp.Header.Get("X-RateLimit-Reset") 

        errStr := fmt.Sprintf(
            "API returned: 429 TooManyRequests. Remaining %v out of %v requests. This will be reset at %v.",
            remainingRequests,
            totalQuota,
            resetTime,
        )

        // TODO
        // Parse reset time to do math on it
        return resetTime, errors.New(errStr)
    }
    return "", nil
}

func getRequest(url string) (*http.Response, error) {
    resp, err := http.Get(url)

    if err != nil {
        return nil, err
    }

    if _, err := checkForRateLimit(resp); err != nil {
        return nil, err
    }

    // TODO
    // handle session auth

    if resp.StatusCode >= 400 {
        return resp, errors.New(fmt.Sprintf(
            "Response code: %v",
            resp.StatusCode,
        ))
    }

    return resp, nil
}

func buildURL(protocol, host string, parts []string, params map[string]string) (string, error) {
    objUrl := &url.URL {
        Scheme: protocol,
        Host: host,
    }
    objUrl = objUrl.JoinPath(parts...)

    queries := objUrl.Query()
    for k, v := range params {
        queries.Set(k, v)
    }

    objUrl.RawQuery = queries.Encode()
    return objUrl.String(), nil
}

func getBody[S any](url string, parseInto *S) error {
    resp, err := getRequest(url)
    if err != nil {
        return err
    }

    body, err := io.ReadAll(resp.Body)
    if err != nil {
        return err
    }

    return json.Unmarshal(body, &parseInto)
}

func postBody[S, R any](url, token string, bodyStruct S, parseInto *R) error {
    body, err := json.Marshal(bodyStruct)
    if err != nil {
        return err
    }

    request, err := http.NewRequest("POST", url, bytes.NewBuffer(body))
    if err != nil {
        return err
    }

    request.Header.Add(
        "Authorization",
        "Bearer " + token,
    )
    request.Header.Add("Content-Type", "application/json")


    client := &http.Client{}
    slog.Debug(
        "Request",
        "Header", request.Header,
        "Body", request.Body,
    )
    response, err := client.Do(request)
    if err != nil {
        return err
    }
    return parseJsonBodyFromResponse(response, parseInto)
}

func parseJsonBodyFromResponse[S any](response *http.Response, parseInto *S) error {
    defer response.Body.Close()
    body, err := io.ReadAll(response.Body)
    slog.Debug("Response", "body", body)

    if response.StatusCode != 200 {
        return errors.New(
            fmt.Sprintf("Received non-positive status code for request: %v", response.StatusCode),
        )
    } else {
        if err != nil {
            return err
        }
        return json.Unmarshal(body, &parseInto)
    }
}
