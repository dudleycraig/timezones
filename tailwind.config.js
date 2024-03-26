module.exports = {
  content: process.env.NODE_ENV == 'production' 
  ? ["./public/js/main.js"] 
  : ["./src/client/**/*.cljs", "./public/js/cljs-runtime/*.js"],
  theme: {
    extend: {},
  },
  plugins: [],
};
