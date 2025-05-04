import express from "express";
import cors from "cors";
import connectDb from "./Phase1/mongodb.js";
import allRoutes from "./Phase1/routes/allRoutes.js";

const app = express();
const port = 5000;

connectDb();

app.use(express.json());

const corsOptions = {
	origin: 'http://localhost:3000', // Reflect the request origin
	credentials: true,
};

app.use(cors(corsOptions));


app.get("/", (req, res) => {
	res.send("Backend is working!");
});

app.use("/api", allRoutes);

app.listen(port, () => {
	console.log(`Backend server is running on port ${port}`);
});