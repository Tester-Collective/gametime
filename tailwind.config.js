/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
      "./src/main/resources/templates/**/*.html",
  ],
  theme: {
    extend: {
      colors: {
        'tokyo-night-bg': '#1a1b26',
        'tokyo-night-variable': '#c0caf5',
        'tokyo-night-red': '#f7768e',
        'tokyo-night-terminal-black': '#414868',
      }
    },
  },
  plugins: [],
}

