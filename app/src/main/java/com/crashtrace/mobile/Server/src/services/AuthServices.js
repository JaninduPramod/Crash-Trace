import { CustomError } from "../middlewares/ErrorMiddleware.js";
import User from "../models/User.js";
import { ApiResponse } from "../response/ApiResponse.js";

export const testingService = async () => {
  let responseType = "failed";

  if (responseType == "success") {
    return "Test successful ...";
  } else if (responseType == "failed") {
    throw new CustomError("Test failed !!!", 401);
  } else {
    throw new CustomError("Unknown Test Response", 500);
  }
};

export const registerUserService = async (data) => {
  if ((data.name && data.nic && data.email && data.password) == "") {
    throw new CustomError("fields must not be empty !!!", 401);
  }
  try {
    // Check if user with the given email already exists
    const existingUser = await User.findOne({ email: data.email });
    if (existingUser) {
      throw new CustomError("Email already registered!", 409);
    }
    // Create new user
    const user = new User({
      name: data.name,
      nic: data.nic,
      email: data.email,
      password: data.password,
    });
    const response = await user.save();
    return new ApiResponse(response, "User registered successfully", true);
  } catch (err) {
    throw new CustomError("Error registering user: " + err.message, 500);
  }
};

export const loginUserService = async (data) => {
  if ((data.email && data.password) == "") {
    throw new CustomError("fields must not be empty !!!", 401);
  }
  // Logic for logging in user goes here
};

export const sendOtpService = async (email) => {
  if (email == "") {
    throw new CustomError("fields must not be empty !!!", 401);
  }
  // Logic for sending OTP goes here
};

export const verifyOtpService = async (data) => {
  if ((data.email && data.otp) == "") {
    throw new CustomError("fields must not be empty !!!", 401);
  }
  // Logic for verifying OTP goes here
};

export const changePasswordService = async (data) => {
  if ((data.email && data.password && data.confirmPassword) == "") {
    throw new CustomError("fields must not be empty !!!", 401);
  }
  if (data.password !== data.confirmPassword) {
    throw new CustomError("Passwords are not same !!!", 401);
  }
  // Logic for change password goes here
};
