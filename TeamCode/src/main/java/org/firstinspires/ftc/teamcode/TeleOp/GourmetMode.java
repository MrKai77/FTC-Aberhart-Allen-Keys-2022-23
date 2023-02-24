package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class GourmetMode extends OpMode {
    DcMotor frontLeftMotor;
    DcMotor frontRightMotor;
    DcMotor backLeftMotor;
    DcMotor backRightMotor;

    DcMotor armMotor;

    Servo rightClaw;
    Servo leftClaw;

    @Override
    public void init() {
        frontLeftMotor = hardwareMap.get(DcMotor.class, "FrontLeft");
        frontRightMotor = hardwareMap.get(DcMotor.class, "FrontRight");
        backLeftMotor = hardwareMap.get(DcMotor.class, "BackLeft");
        backRightMotor = hardwareMap.get(DcMotor.class, "BackRight");

        frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotor.Direction.REVERSE);

        armMotor = hardwareMap.get(DcMotor.class, "ArmMotor");
        rightClaw = hardwareMap.get(Servo.class, "RightClaw");
        leftClaw =  hardwareMap.get(Servo.class, "LeftClaw");
    }

    @Override
    public void loop() {
        setDriveTrainSpeeds();
        setArmStates();
    }

    public void setArmStates() {
        if (gamepad1.b) {    // Close claw
            rightClaw.setPosition(0);
            leftClaw.setPosition(1);
        }
        if (gamepad1.x) {    // Open claw
            rightClaw.setPosition(0.5);
            leftClaw.setPosition(0.5);
        }

        armMotor.setPower(0.1 + (gamepad1.right_trigger - gamepad1.left_trigger/1.5));
    }

    public void setDriveTrainSpeeds() {
        double FLMotorSpeed;
        double FRMotorSpeed;
        double BLMotorSpeed;
        double BRMotorSpeed;

        if (gamepad1.dpad_right) {
            FLMotorSpeed = -1;
            FRMotorSpeed = 1;
            BRMotorSpeed = -1;
            BLMotorSpeed = 1;
        } else if(gamepad1.dpad_left) {
            FLMotorSpeed = 1;
            FRMotorSpeed = -1;
            BRMotorSpeed = 1;
            BLMotorSpeed = -1;
        } else {
            FLMotorSpeed = gamepad1.left_stick_y;
            BLMotorSpeed = gamepad1.left_stick_y;

            FRMotorSpeed = gamepad1.right_stick_y;
            BRMotorSpeed = gamepad1.right_stick_y;
        }

        double motorSpeedDivider = getMotorSpeedDivider();

        frontLeftMotor.setPower(FLMotorSpeed / motorSpeedDivider);
        backLeftMotor.setPower(BLMotorSpeed / motorSpeedDivider);
        frontRightMotor.setPower(FRMotorSpeed / motorSpeedDivider);
        backRightMotor.setPower(BRMotorSpeed / motorSpeedDivider);
    }

    public Double getMotorSpeedDivider() {
        double motorSpeedDivider = 1.5;

        if (gamepad1.right_bumper) {
            motorSpeedDivider = 1;
        } else if (gamepad1.left_bumper) {
            motorSpeedDivider = 2;
        }
        return motorSpeedDivider;
    }
}