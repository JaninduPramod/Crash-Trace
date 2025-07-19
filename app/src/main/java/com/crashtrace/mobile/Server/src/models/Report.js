import mongoose from "mongoose";

const reportSchema = new mongoose.Schema(
  {
    vehicleNo: { type: String, required: true },
    imageUrl: { type: String },
    description: { type: String, required: true },
    location: {
      lat: { type: Number, required: true },
      lng: { type: Number, required: true },
    },
    address: { type: String },
    reporterId: { type: mongoose.Schema.Types.ObjectId, ref: "User", required: true },
    date: { type: Date, required: true },
    isApproved: { type: Boolean, default: false },
    trustRate: { type: Number, default: 0 },
    upVotes: [{ type: mongoose.Schema.Types.ObjectId, ref: "User" }],
    downVotes: [{ type: mongoose.Schema.Types.ObjectId, ref: "User" }],
  },
  { timestamps: { createdAt: true, updatedAt: false } }
);

const Report = mongoose.model("Report", reportSchema);
export default Report;
