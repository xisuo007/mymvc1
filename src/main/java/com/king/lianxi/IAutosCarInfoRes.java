package com.king.lianxi;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
public class IAutosCarInfoRes {

    /**
     * info : 测试内容l4ol
     * result : {"brandList":[{"carBrandId":"27","carBrandName":"长丰","initial":"C"}],"modelList":[{"carModelName":"测试内容68t0","carSeriesId":"测试内容hat8","displacement":"测试内容dj28","jsonData":"测试内容s16b","price":13370,"productionYear":"测试内容o49e","transmissionType":"测试内容mu8u","versionDate":"测试内容9s42","versionYear":"测试内容gyot","carModelId":"19009"}],"seriesList":[{"carBrandId":"测试内容78ah","carFactoryId":"测试内容fgcq","carFactoryName":"测试内容8se2","carSeriesId":"测试内容3516","carSeriesName":"测试内容i68j"}]}
     * success : false
     */

    private String info;
    private ResultBean result;
    private boolean success;

    @NoArgsConstructor
    @Data
    public static class ResultBean{
        private DataList data;
        private Boolean flag;
        private String statusCode;
    }

    @NoArgsConstructor
    @Data
    public static class DataList {
        private List<BrandListBean> brandList;
        private List<ModelListBean> modelList;
        private List<SeriesListBean> seriesList;

        @NoArgsConstructor
        @Data
        public static class BrandListBean {
            /**
             * carBrandId : 27
             * carBrandName : 长丰
             * initial : C
             */

            private String carBrandId;
            private String carBrandName;
            private String initial;
        }

        @NoArgsConstructor
        @Data
        public static class ModelListBean {
            /**
             * carModelName : 测试内容68t0
             * carSeriesId : 测试内容hat8
             * displacement : 测试内容dj28
             * jsonData : 测试内容s16b
             * price : 13370
             * productionYear : 测试内容o49e
             * transmissionType : 测试内容mu8u
             * versionDate : 测试内容9s42
             * versionYear : 测试内容gyot
             * carModelId : 19009
             */
            private IAutosCarModelDetail carModelDetail;
            private String carModelName;
            private String carSeriesId;
            private String displacement;
            private String jsonData;
            private BigDecimal price;
            private String productionYear;
            private String transmissionType;
            private String versionDate;
            private String versionYear;
            private String carModelId;
        }

        @NoArgsConstructor
        @Data
        public static class SeriesListBean {
            /**
             * carBrandId : 测试内容78ah
             * carFactoryId : 测试内容fgcq
             * carFactoryName : 测试内容8se2
             * carSeriesId : 测试内容3516
             * carSeriesName : 测试内容i68j
             */

            private String carBrandId;
            private String carFactoryId;
            private String carFactoryName;
            private String carSeriesId;
            private String carSeriesName;
        }
    }
}
