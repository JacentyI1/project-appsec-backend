# Akkoma ebooks

This is the code of [awawa.cat](https://awawa.cat) ebooks bot.

## Features
 - multi-user support
 - Own markov textgen
 - Fast
 - Contains own posts cache

## Akkoma package

The akkoma API client is a small subset of all functionality. Nevertheless it
allows reading and making posts (with token). I haven't tested it on Mastodon
or even vanilla Pleroma. If that's what's you're looking for feel free to use
it (or even report bugs).

If you would like that code in separate module, write me a DM.

## Note on akkoma OpenApi

I've tried to use different OpenApi codegens to generate client. This has
failed due to:
 - A lack of good codegen for Golang. This is a really big problem for Go
   ecosystem. The most complete I found is written in PHP! Look for
   swaggest/swac on github. Rest of them just suck.
 - Errors in generated doc. I've downloaded doc from
   [awawa.cat](https://awawa.cat/api/openapi) and right of the bat it contained
   a duplicated properties. After manual adjustments it worked on some of the
   codegens but then again, I still wasn't able to generate full code. One of
   the codegens even crashed leaving me with half-generated .go file. I
   discovered many more errors with an validator suite.

This led me to abadon this approach. After all I only need 2 or 3 entries so
I'm able to write it myself.

## Note on Go modules

Due to go implementation of modules, main module needs to be placed in main
directory of this repository. I would like to have it moved to src but it seems
this is really complicatd and would add additional part to import statements.

To minimize number of `.go` files in main directory I will use subpackages
(subdirectories) for any extra functionality.
