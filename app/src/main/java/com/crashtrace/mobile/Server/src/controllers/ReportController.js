import { createReportService } from "../services/ReportServices.js";

export const createReport = async (req, res, next) => {
  try {
    const reporterId = req._id
    const response = await createReportService(req.body, reporterId);
    res.status(201).json(response);
  } catch (error) {
    next(error);
  }
};