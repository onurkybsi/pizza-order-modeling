const express = require("express");
const path = require("path");
const http = require("http");
require("dotenv").config();

const HTT_OK_STATUS_CODE = 200;
const HTTP_GET_VERB = "GET";

http.request(
  {
    hostname: process.env.MODEL_DATA_PRODUCER_BASE_URL,
    port: process.env.MODEL_DATA_PRODUCER_PORT,
    path: process.env.MODEL_DATA_PRODUCER_HEALTH_CHECK_END_POINT,
    method: HTTP_GET_VERB,
  },
  (res) => {
    if (res.statusCode !== HTT_OK_STATUS_CODE)
      throw new Error("Couldn't connected to ModelDataProducer!");
  }
);

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
