import mongoose from "mongoose";

const reportSchema = new mongoose.Schema(
  {
    vehicleNo: { type: String, required: true },
    cardID: { type: String, unique: true }, // Incrementing string
    title: { type: String, required: false,default: "Untitled Report" },
    imageUrl: { type: String, required: false },
    description: { type: String, required: false },
    location: { type: [String], required: false },
    address: { type: String },
    reporterId: { type: mongoose.Schema.Types.ObjectId, ref: "User", required: true },
    date: { type: String, required: false },
    status: { type: String, default: "pending" },
    trustRate: { type: Number, default: 0 },
    damageRate: { type: Number, default: 0 },
  },
  { timestamps: { createdAt: true, updatedAt: false } }
);

// Pre-save hook to increment cardID
reportSchema.pre("save", async function (next) {
  if (!this.isNew) return next(); // Only generate cardID for new documents

  const lastReport = await mongoose.model("Report").findOne().sort({ createdAt: -1 });
  const lastCardID = lastReport ? parseInt(lastReport.cardID.split("-")[1]) : 0;

  this.cardID = `Report-${lastCardID + 1}`; // Increment and format as string
  next();
});

const Report = mongoose.model("Report", reportSchema);
export default Report;