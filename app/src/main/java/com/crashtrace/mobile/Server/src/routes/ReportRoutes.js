import express from "express";
import { createReport,getApprovedReports,searchReport } from "../controllers/ReportController.js";
import { verifyToken } from "../middlewares/AuthMiddleware.js";

const ReportRoutes = express.Router();

ReportRoutes.post("/create", verifyToken, createReport);
ReportRoutes.get("/approvedReports", verifyToken, getApprovedReports);
ReportRoutes.post("/searchReport", verifyToken, searchReport);

export default ReportRoutes;
