import { resolve } from 'path'
import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vitejs.dev/config/
export default defineConfig({
    build: {
        lib: {
            entry: resolve(__dirname, 'src/index.js'),
            formats: ["es"],
        },
        outDir: 'build/vite',
        rollupOptions: {
            external: [
                'react',
                'react-dom',
            ],
            output: {
                entryFileNames: "[name].js",
            },
        }
    },
    plugins: [
        react({
            jsxRuntime: 'classic',
        })
    ]
})
