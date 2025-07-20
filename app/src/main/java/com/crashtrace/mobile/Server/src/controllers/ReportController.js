import { createReportService,getApprovedReportsService } from "../services/ReportServices.js";

export const createReport = async (req, res, next) => {
  try {
    const reporterId = req._id
    
    const response = await createReportService(req.body, reporterId);
    res.status(201).json(response);
  } catch (error) {
    next(error);
  }
};

export const getApprovedReports = async (req, res, next) => {
  try {
    const response = await getApprovedReportsService();
    res.status(200).json(response);
  } catch (error) {
    next(error);
  }
};