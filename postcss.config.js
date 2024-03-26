module.exports = (ctx) => ({
  plugins: { 
    tailwindcss: {},
    autoprefixer: {},
    cssnano: process.env.NODE_ENV === 'production' ? {} : false,
  }
})
