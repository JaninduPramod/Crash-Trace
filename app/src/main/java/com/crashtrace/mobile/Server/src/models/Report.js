import mongoose from "mongoose";

const reportSchema = new mongoose.Schema(
  {
    vehicleNo: { type: String, required: true },
    imageUrl: { type: String ,required: false},
    description: { type: String, required: true },
    location: { type: [String], required: true }, 
    address: { type: String },
    reporterId: { type:String, required: true },
    date: { type: String, required: true },
    isApproved: { type: Boolean, default: false },
    trustRate: { type: Number, default: 0 }
  },
  { timestamps: { createdAt: true, updatedAt: false } }
);

const Report = mongoose.model("Report", reportSchema);
export default Report;
