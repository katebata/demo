FROM node:19-alpine AS builder
ARG api_base_url
WORKDIR /app


ENV VITE_API_BASE_URL=${api_base_url}

COPY package.json package-lock.json ./
RUN npm ci

COPY . .
RUN npm run build


FROM nginx:stable-alpine

RUN rm -rf /usr/share/nginx/html/*
COPY --from=builder /app/dist /usr/share/nginx/html


EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
