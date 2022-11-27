package fun.implementsstudio.mathforhim.service.impl;

import com.aliyun.facebody20191230.models.*;
import com.aliyun.tea.TeaException;
import fun.implementsstudio.mathforhim.service.IFaceRecService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FaceRecServiceImpl implements IFaceRecService {

    /**
     * 创建人脸数据库
     *
     * @param client
     * @param dbName
     * @return void
     * @throws Exception
     */
    public Integer createFaceDb(com.aliyun.facebody20191230.Client client, String dbName) throws Exception {
        try {
            CreateFaceDbRequest requestBody = new CreateFaceDbRequest()
                    .setName(dbName);
            CreateFaceDbResponse faceDbResponse = client.createFaceDb(requestBody);
            if (faceDbResponse != null && faceDbResponse.statusCode.equals(200)) {
                log.info("--------------------创建人脸数据库成功--------------------");
                return 1;
            }
        } catch (TeaException err) {
            log.error("create facebody db error:{}", err.message);
        } catch (Exception _err) {
            TeaException err = new TeaException(_err.getMessage(), _err);
            log.error("create facebody db error:{}", err.message);
        }
        return -1;
    }

    /**
     * 添加人脸样本
     *
     * @param dbName   数据库名称
     * @param entityId 实体ID
     * @return void
     * @throws Exception
     */
    public Integer addFaceEntity(com.aliyun.facebody20191230.Client client, String dbName, String entityId) throws Exception {
        try {
            AddFaceEntityRequest requestBody = new AddFaceEntityRequest();
            requestBody.dbName = dbName;
            requestBody.entityId = entityId;
            AddFaceEntityResponse addFaceEntityResponse = client.addFaceEntity(requestBody);
            if (addFaceEntityResponse != null && addFaceEntityResponse.statusCode.equals(200)) {
                log.info("--------------------创建人脸样本成功--------------------");
                return 1;
            }
        } catch (TeaException err) {
            log.error("add face entity error.:{}", err.message);
        } catch (Exception _err) {
            TeaException err = new TeaException(_err.getMessage(), _err);
            log.error("add face entity error.:{}", err.message);
        }
        return -1;
    }

    /**
     * 添加人脸数据
     *
     * @param dbName   数据库名称
     * @param entityId 实体ID
     * @param imageUrl 人脸图片地址，必须是同Region的OSS的图片地址。人脸必须是正面无遮挡单人人脸。
     * @return void
     * @throws Exception
     */
    public Integer addFace(com.aliyun.facebody20191230.Client client, String dbName, String entityId, String imageUrl) throws Exception {
        try {
            AddFaceRequest requestBody = new AddFaceRequest();
            requestBody.dbName = dbName;
            requestBody.entityId = entityId;
            requestBody.imageUrl = imageUrl;
            AddFaceResponse addFaceResponse = client.addFace(requestBody);
            if (addFaceResponse!=null && addFaceResponse.statusCode.equals(200)){
                log.info("--------------------创建人脸数据成功--------------------");
                return 1;
            }
        } catch (TeaException err) {
            log.error("add face error.:{}", err.message);
        } catch (Exception _err) {
            TeaException err = new TeaException(_err.getMessage(), _err);
            log.error("add face error.:{}", err.message);
        }
        return -1;
    }

    /**
     * 搜索人脸
     *
     * @param dbName   数据库名称
     * @param imageUrl 图片URL地址。必须是同Region的OSS地址
     * @param limit    搜索结果数量限制
     * @return Facebody.SearchFaceResponse
     * @throws Exception
     */
    public SearchFaceResponse searchFace(com.aliyun.facebody20191230.Client client, String dbName, String imageUrl, Integer limit) throws Exception {
        try {
            SearchFaceRequest requestBody = new SearchFaceRequest();
            requestBody.dbName = dbName;
            requestBody.imageUrl = imageUrl;
            requestBody.limit = limit;
            return client.searchFace(requestBody);
        } catch (TeaException err) {
            log.error("search face error.:{}",err.message);
        } catch (Exception _err) {
            TeaException err = new TeaException(_err.getMessage(), _err);
            log.error("search face error.:{}",err.message);
        }
        return null;
    }

    /**
     * 人脸比对 1:1
     *
     * @param imageUrlA 待比对图片A的URL地址。必须是同Region的OSS地址
     * @param imageUrlB 待比对图片B的URL地址。必须是同Region的OSS地址
     * @return Facebody.SearchFaceResponseData
     * @throws Exception
     */
    public CompareFaceResponse compareFace(com.aliyun.facebody20191230.Client client, String imageUrlA, String imageUrlB) throws Exception {
        try {
            CompareFaceRequest requestBody = new CompareFaceRequest();
            requestBody.imageURLA = imageUrlA;
            requestBody.imageURLB = imageUrlB;
            return client.compareFace(requestBody);
        } catch (TeaException err) {
            log.error("compare face error.:{}",err.message);
        } catch (Exception _err) {
            TeaException err = new TeaException(_err.getMessage(), _err);
            log.error("compare face error.:{}",err.message);
        }
        return null;
    }

//    public static void main(String[] args_) throws Exception {
//        java.util.List<String> args = java.util.Arrays.asList(args_);
//        String regionId = args.get(0);
//        String dbName = args.get(1);
//        String entityId = args.get(2);
//        java.util.List<String> faceUrlArr = com.aliyun.darabonbastring.Client.split(args.get(1), ",", 10);
//        String testImgUrl = args.get(2);
//        String testCompareImgUrl = args.get(3);
//        // 获取人脸数据库Client
//        com.aliyun.facebody20191230.Client client = Sample.createClient(regionId);
//        // 创建人脸数据库
//        Sample.createFaceDb(client, dbName);
//        // 创建人脸样本
//        Sample.addFaceEntity(client, dbName, entityId);
//        // 为人脸样本添加图片
//        for (String url : faceUrlArr) {
//            Sample.addFace(client, dbName, entityId, url);
//        }
//        // 用测试图片去人脸数据库中查找
//        SearchFaceResponse searchFaceResponse = Sample.searchFace(client, dbName, testImgUrl, 10);
//        com.aliyun.teaconsole.Client.log(com.aliyun.teautil.Common.toJSONString(TeaModel.buildMap(searchFaceResponse)));
//        // 测试 1:1 人脸比对
//        CompareFaceResponse compar public static void main(String[] args_) throws Exception {
////        java.util.List<String> args = java.util.Arrays.asList(args_);
////        String regionId = args.get(0);
////        String dbName = args.get(1);
////        String entityId = args.get(2);
////        java.util.List<String> faceUrlArr = com.aliyun.darabonbastring.Client.split(args.get(1), ",", 10);
////        String testImgUrl = args.get(2);
////        String testCompareImgUrl = args.get(3);
////        // 获取人脸数据库Client
////        com.aliyun.facebody20191230.Client client = Sample.createClient(regionId);
////        // 创建人脸数据库
////        Sample.createFaceDb(client, dbName);
////        // 创建人脸样本
////        Sample.addFaceEntity(client, dbName, entityId);
////        // 为人脸样本添加图片
////        for (String url : faceUrlArr) {
////            Sample.addFace(client, dbName, entityId, url);
////        }
////        // 用测试图片去人脸数据库中查找
////        SearchFaceResponse searchFaceResponse = Sample.searchFace(client, dbName, testImgUrl, 10);
////        com.aliyun.teaconsole.Client.log(com.aliyun.teautil.Common.toJSONString(TeaModel.buildMap(searchFaceResponse)));
////        // 测试 1:1 人脸比对
////        CompareFaceResponse compareFaceResponse = Sample.compareFace(client, testImgUrl, testCompareImgUrl);
////        com.aliyun.teaconsole.Client.log(com.aliyun.teautil.Common.toJSONString(TeaModel.buildMap(compareFaceResponse)));
////    }eFaceResponse = Sample.compareFace(client, testImgUrl, testCompareImgUrl);
//        com.aliyun.teaconsole.Client.log(com.aliyun.teautil.Common.toJSONString(TeaModel.buildMap(compareFaceResponse)));
//    }
}
