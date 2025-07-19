import Report from "../models/Report.js";
import { CustomError } from "../middlewares/ErrorMiddleware.js";
import { ApiResponse } from "../response/ApiResponse.js";

export const createReportService = async (data, reporterId) => {


  try {
    const report = new Report({
      vehicleNo: data.vehicleNo,
      description: data.description,
      location: data.location.split(","),
      address: data.address,
      reporterId: reporterId,
      date: data.date,
    });
    await report.save();
    return new ApiResponse(null, "Report created successfully", true);
  } catch (error) {
    throw new CustomError("Failed to create report", 200);
  }
};
