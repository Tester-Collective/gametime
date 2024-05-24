/** @type {import('tailwindcss').Config} */
module.exports = {
  darkMode: 'class',
  content: [
    "./src/main/resources/templates/**/*.html",
    "./node_modules/flowbite/**/*.js",
  ],
  theme: {
    extend: {
      width: {
        '108': '27rem',
        '128': '32rem',
        '144': '36rem',
      },
      colors: {
        'tokyo-night-bg': '#1a1b26',
        'card-bg': '#1f202d',
        'navbar-bg': '#15151c',
        'tokyo-night-variable': '#c0caf5',
        'tokyo-night-red': '#f7768e',
        'tokyo-night-terminal-black': '#414868',
        primary: {"50":"#eff6ff","100":"#dbeafe","200":"#bfdbfe","300":"#93c5fd","400":"#60a5fa","500":"#3b82f6","600":"#2563eb","700":"#1d4ed8","800":"#1e40af","900":"#1e3a8a","950":"#172554"},
      },
      fontFamily: {
        'body': [
          'Inter',
          'ui-sans-serif',
          'system-ui',
          '-apple-system',
          'system-ui',
          'Segoe UI',
          'Roboto',
          'Helvetica Neue',
          'Arial',
          'Noto Sans',
          'sans-serif',
          'Apple Color Emoji',
          'Segoe UI Emoji',
          'Segoe UI Symbol',
          'Noto Color Emoji'
        ],
        'sans': [
          'Inter',
          'ui-sans-serif',
          'system-ui',
          '-apple-system',
          'system-ui',
          'Segoe UI',
          'Roboto',
          'Helvetica Neue',
          'Arial',
          'Noto Sans',
          'sans-serif',
          'Apple Color Emoji',
          'Segoe UI Emoji',
          'Segoe UI Symbol',
          'Noto Color Emoji'
        ]
      }
    },
  },
  plugins: [
    require('flowbite/plugin'),
  ],
}
