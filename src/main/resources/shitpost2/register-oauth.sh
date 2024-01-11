#!/bin/bash
# This script is based on:
#
# by a1batross
#
# This is scheduled for rewrite and inclusion in main daemon code
# Tag for grepping: TODO

cat <<-EONEED

    Needed paraters:
     - CLIENT_NAME
     - INSTANCE_URL

    Please fill them out by doing:
      export INSTANCE_URL=https://awawa.cat
      export CLIENT_NAME=ebooksApp
    before you start the script


EONEED

set -euo pipefail

cat <<-EOHEAD

    This script will register new OAUTH2 application with:
      CLIENT_NAME=${CLIENT_NAME}
      INSTANCE_URL=${INSTANCE_URL}

    During registation you will need to open browser and login with an user account.
    Please prepare login credentials.

    After registration you will be given CLIENT_ID CLIENT_SECRET from this terminal.
    You will be able to read CLIENT_TOKEN from your browser.
    You need provide all 3 of them in ebooks configuration file.

    You have 10 seconds to stop this script ( with CTRL+C ).

EOHEAD

sleep 10
echo "Proceeding..."

scopes='read write'
response=$( curl -XPOST \
    -F "client_name=${CLIENT_NAME}" \
    -F "redirect_uris=urn:ietf:wg:oauth:2.0:oob" \
    -F "scopes=$scopes" \
    ${INSTANCE_URL}/api/v1/apps
)
client_id=$(echo $response | jq -r .client_id)
client_secret=$(echo $response | jq -r .client_secret)

cat <<-EOLINK 

    Now open this in your browser:
        ${INSTANCE_URL}/oauth/authorize?client_id=${client_id}&redirect_uri=urn:ietf:wg:oauth:2.0:oob&response_type=code&scope=$(echo $scopes | sed s/\ /+/g)

    Client id: $client_id
    Client secret: $client_secret
    Client token: read from your browser

EOLINK

