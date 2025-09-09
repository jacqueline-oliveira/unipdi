# Etapa 1: build com Vite
FROM node:18-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm ci
COPY . .

# Define variável para API
ARG VITE_API_URL="/api"
ENV VITE_API_URL=${VITE_API_URL}

# Gera .env.production para Vite usar
RUN echo "VITE_API_URL=${VITE_API_URL}" > .env.production

# Build do projeto
RUN npm run build

# Etapa 2: Nginx para servir o frontend
FROM nginx:alpine
COPY --from=build /app/dist /usr/share/nginx/html
COPY nginx/default.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
