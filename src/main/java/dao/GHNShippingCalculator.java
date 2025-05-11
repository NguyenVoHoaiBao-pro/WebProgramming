package dao;

import okhttp3.*;

import java.io.IOException;

public class GHNShippingCalculator {
    private static final String GHN_URL = "https://online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/fee";
    private static final String GHN_TOKEN = "YOUR_TOKEN_HERE";
    private static final String SHOP_ID = "YOUR_SHOP_ID";

    public static String getShippingFee(int fromDistrict, int toDistrict, String toWardCode, int weight) throws IOException {
        OkHttpClient client = new OkHttpClient();

        String json = String.format("""
        {
          "service_type_id": 2,
          "insurance_value": 100000,
          "coupon": null,
          "from_district_id": %d,
          "to_district_id": %d,
          "to_ward_code": "%s",
          "height": 15,
          "length": 15,
          "weight": %d,
          "width": 15
        }
        """, fromDistrict, toDistrict, toWardCode, weight);

        Request request = new Request.Builder()
                .url(GHN_URL)
                .addHeader("Token", GHN_TOKEN)
                .addHeader("ShopId", SHOP_ID)
                .addHeader("Content-Type", "application/json")
                .post(RequestBody.create(json, MediaType.parse("application/json")))
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}

