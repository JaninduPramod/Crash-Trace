import Report from "../models/Report.js";
import { CustomError } from "../middlewares/ErrorMiddleware.js";
import { ApiResponse } from "../response/ApiResponse.js";

export const createReportService = async (data, reporterId) => {
  try {
    const report = new Report({
      ...data,
      reporterId,
      date: data.date || new Date(),
    });
    const savedReport = await report.save();
    return new ApiResponse(savedReport, "Report created successfully", true);
  } catch (error) {
    throw new CustomError("Failed to create report", 500);
  }
};