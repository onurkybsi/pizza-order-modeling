const express = require("express");
const path = require("path");
require("dotenv").config();

const router = express.Router();

const configureClientAppRoute = (function (_router) {
  _router.use(
    express.static(path.join(__dirname, process.env.STATIC_CONTENT_PATH))
  );

  _router.get("/", (req, res) => {
    res.sendFile(path.join(__dirname, process.env.STATIC_CONTENT_PATH));
  });
  _router.get("*", function (req, res) {
    res.redirect("/");
  });
})(router);

const app = express()
  .use(router)
  .listen(process.env.PORT, () =>
    console.log(`UserInterface app on ${process.env.PORT}`)
  );
