import mongoose from "mongoose";
import AutoIncrementFactory from "mongoose-sequence";

const AutoIncrement = AutoIncrementFactory(mongoose);



const reportSchema = new mongoose.Schema(
  {
    vehicleNo: { type: String, required: true },
    cardID: { type: Number, unique: true },
    imageUrl: { type: String ,required: false},
    description: { type: String, required: false },
    location: { type: [String], required: false }, 
    address: { type: String },
    reporterId: { type: mongoose.Schema.Types.ObjectId, ref: "User", required: true }, 
    date: { type: String, required: false },
    status: { type: String, default: "pending" },
    trustRate: { type: Number, default: 0 }
  },
  { timestamps: { createdAt: true, updatedAt: false } }
);

reportSchema.plugin(AutoIncrement, { inc_field: "cardID" });


const Report = mongoose.model("Report", reportSchema);
export default Report;
