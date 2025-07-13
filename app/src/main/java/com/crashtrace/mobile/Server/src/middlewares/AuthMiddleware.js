import jwt from "jsonwebtoken";
import User from "../models/User.js";
import { CustomError } from "./ErrorMiddleware.js";

export const verifyToken = async (req, res, next) => {
  let token;

  if (
    req.headers.authorization &&
    req.headers.authorization.startsWith("Bearer")
  ) {
    try {
      token = req.headers.authorization.split(" ")[1];

      const decoded = jwt.verify(token, process.env.JWT_SECRET);
      console.log("Decoded token:", decoded.id);

      req.user = await User.findById(decoded.id).select("-password");

      if (!req.user) {
        throw new CustomError("User not found", 404);
      }

      next();
    } catch (error) {
      throw new CustomError("Not authorized, token failed", 401);
    }
  }

  if (!token) {
    throw new CustomError("Not authorized, no token", 401);
  }
};
