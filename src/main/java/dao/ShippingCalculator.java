package dao;

import java.util.HashMap;
import java.util.Map;

public class ShippingCalculator {
    // Bảng giá theo tỉnh/quận
    private static final Map<String, RegionFee> regionFees = new HashMap<>();

    static {
        // Khởi tạo các phí theo khu vực
        regionFees.put("TP.HCM", new RegionFee(15000, 3000, 2000));
        regionFees.put("Hà Nội", new RegionFee(18000, 4000, 2500));
        regionFees.put("Đà Nẵng", new RegionFee(17000, 3500, 2200));
        regionFees.put("Quận 1, TP.HCM", new RegionFee(12000, 2500, 1800));
        regionFees.put("Quận 3, TP.HCM", new RegionFee(14000, 3000, 2000));
    }

    public static int calculateShippingFee(String region, double distanceKm, double weightKg) {
        // Kiểm tra xem khu vực có trong bảng giá không
        if (regionFees.containsKey(region)) {
            RegionFee fee = regionFees.get(region);
            return fee.getBaseFee() + (int)(distanceKm * fee.getCostPerKm()) + (int)(weightKg * fee.getCostPerKg());
        } else {
            // Nếu không có trong bảng giá, trả về phí mặc định
            System.out.println("Khu vực không có trong bảng giá, áp dụng phí mặc định.");
            return 20000 + (int)(distanceKm * 3000) + (int)(weightKg * 2500);
        }
    }

    public static void main(String[] args) {
        String region = "TP.HCM"; // Khu vực nhận hàng
        double distance = 10.5; // km
        double weight = 2.3; // kg
        int fee = calculateShippingFee(region, distance, weight);
        System.out.println("Phí vận chuyển cho khu vực " + region + ": " + fee + " VND");
    }

    // Lớp lưu trữ các mức phí cho từng khu vực
    static class RegionFee {
        private int baseFee;
        private int costPerKm;
        private int costPerKg;

        public RegionFee(int baseFee, int costPerKm, int costPerKg) {
            this.baseFee = baseFee;
            this.costPerKm = costPerKm;
            this.costPerKg = costPerKg;
        }

        public int getBaseFee() {
            return baseFee;
        }

        public int getCostPerKm() {
            return costPerKm;
        }

        public int getCostPerKg() {
            return costPerKg;
        }
    }
}
