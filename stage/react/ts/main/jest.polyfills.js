const { TextDecoder, TextEncoder, ReadableStream } = require("node:util")

Object.defineProperties(globalThis, {
    ReadableStream: { value: ReadableStream },
    TextDecoder: { value: TextDecoder },
    TextEncoder: { value: TextEncoder }
})


