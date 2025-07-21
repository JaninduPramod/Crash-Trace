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

// function to get all reports
export const getAllReportsService = async () => {
  try {
    const reports = await Report.find().populate("reporterId", "-_id name");
    if (!reports || reports.length === 0) {
      throw new CustomError("No reports found", 200);
    }
    return new ApiResponse(reports, "Reports retrieved successfully", true);
  } catch (error) {
    throw new CustomError("Failed to retrieve reports", 200);
  }
};


// function to get all approved reports
export const getApprovedReportsService = async () => {
    try {
        const reports = await Report.find({ status: "ok" });
        if (!reports || reports.length === 0) {
        throw new CustomError("No reports found", 200);
        }
        return new ApiResponse(reports, "Approved reports retrieved successfully", true);
    } catch (error) {
        throw new CustomError("Failed to retrieve approved reports", 200);
    }
}

// function to search reports by vehicle number
export const searchReportService = async (vehicleNo) => {

  try {
    const report = await Report.findOne({ vehicleNo: vehicleNo }).populate("reporterId", "-_id name");
    if (!report) {
      throw new CustomError("No reports found for this vehicle number", 200);
    }
    return new ApiResponse(report, "Reports retrieved successfully", true);
  } catch (error) {
    throw new CustomError("Failed to retrieve reports", 200);
  }

}
