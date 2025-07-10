# Stage 1: Build Vite app
FROM node:18-alpine as build  
# --> alpine linux base image , comanda folosita pentru a crea aplicatia and name it as build
WORKDIR /app
# --> set the working diretory to "/app"
COPY package*.json ./
# --> take all the things from package.json copy them into "/app"
RUN npm install
COPY . .
RUN npm run build
# install dependenceis , copy the project into the containter and builds the app
# Stage 2: Serve with nginx
FROM nginx:alpine
#---> nginx is a web server that will serve the built app
COPY --from=build /app/dist /usr/share/nginx/html
EXPOSE 80
#--> copy the built app from the build stage to the nginx html directory using default HTTP port (80)
CMD ["nginx", "-g", "daemon off;"]
#--> ryb Nginx in the foreground so that the container keeps running
# --> this is the command that will be run when the container starts