import express from "express";
import "express-async-errors";
import cors from "cors";
import { errorHandler } from "./middlewares/ErrorMiddleware.js";
import AuthRoutes from "./routes/AuthRoutes.js";

const app = express();

app.use(cors());
app.use(express.json());

app.use("/api/auth", AuthRoutes);

app.use(errorHandler);
export default app;
