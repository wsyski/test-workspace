import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

export default defineConfig({
  base: '/o/vacation-test-portlet',
  build: {
    outDir: './vite-build',
    rollupOptions: {
      external: [
        '@liferay/vacation-shared-code',
        'react',
        'react-dom',
        /^(?!@clayui\/css)@clayui.*$/,
      ],
    }
  },
  plugins: [
    react({
      jsxRuntime: 'classic',
    }),
  ]
})
