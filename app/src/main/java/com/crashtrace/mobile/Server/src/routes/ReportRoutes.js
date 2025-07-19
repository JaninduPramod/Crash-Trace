import express from "express";
import { createReport } from "../controllers/ReportController.js";
import { verifyToken } from "../middlewares/AuthMiddleware.js";

const ReportRoutes = express.Router();

ReportRoutes.post("/create", verifyToken, createReport);

export default ReportRoutes;
