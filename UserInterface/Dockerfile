FROM node:10 AS client
WORKDIR /client
COPY client/ ./
RUN npm install && npm run build

FROM node:10 AS server
WORKDIR /server
COPY --from=client /client/build ./build
COPY server/.env ./
COPY server/package*.json ./
RUN npm install
COPY server/index.js ./

EXPOSE 3000

CMD ["node", "./index.js"]