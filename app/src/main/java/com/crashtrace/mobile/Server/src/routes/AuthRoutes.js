import express from "express";
import {
  registerUser,
  testing,
  loginUser,
  sendOtp,
  verifyOtp,
  changePassword,
} from "../controllers/AuthController.js";

const AuthRoutes = express.Router();

AuthRoutes.get("/test", testing);
AuthRoutes.post("/register", registerUser);
AuthRoutes.post("/login", loginUser);
AuthRoutes.post("/sendOtp", sendOtp);
AuthRoutes.post("/verifyOtp", verifyOtp);
AuthRoutes.post("/newPassword", changePassword);

export default AuthRoutes;
