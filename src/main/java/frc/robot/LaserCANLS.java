package frc.robot;

import au.grapplerobotics.LaserCan;

public class LaserCANLS {
    private final LaserCan m_LC;

    private double m_triggertDistance = 20;

    public LaserCANLS(int canID) {
        m_LC = new LaserCan(canID);

        try {
            m_LC.setRangingMode(LaserCan.RangingMode.SHORT);
            m_LC.setRegionOfInterest(new LaserCan.RegionOfInterest(8, 8, 16, 16));
            m_LC.setTimingBudget(LaserCan.TimingBudget.TIMING_BUDGET_20MS);
        } catch (Exception e) {
            System.out.println("LaserCAN ID: " + canID + " Configuration Failed: " + e);
        }
    }

    public boolean get() {
        LaserCan.Measurement measurement = m_LC.getMeasurement();
        if(measurement != null && measurement.status == LaserCan.LASERCAN_STATUS_VALID_MEASUREMENT && measurement.distance_mm >= m_triggertDistance) {
            return true;
        } else {
            return false;
        }
    }

    public void setTriggerDistance(double distance) {
        m_triggertDistance = distance;
    }

    public double getTriggerDistance() {
        return m_triggertDistance;
    }

}
