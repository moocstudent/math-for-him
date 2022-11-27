package fun.implementsstudio.mathforhim.service;

import com.aliyun.facebody20191230.models.CompareFaceResponse;
import com.aliyun.facebody20191230.models.SearchFaceResponse;

public interface IFaceRecService {

    Integer createFaceDb(com.aliyun.facebody20191230.Client client, String dbName) throws Exception;

    Integer addFaceEntity(com.aliyun.facebody20191230.Client client, String dbName, String entityId) throws Exception;

    Integer addFace(com.aliyun.facebody20191230.Client client, String dbName, String entityId, String imageUrl) throws Exception;

    SearchFaceResponse searchFace(com.aliyun.facebody20191230.Client client, String dbName, String imageUrl, Integer limit) throws Exception;

    CompareFaceResponse compareFace(com.aliyun.facebody20191230.Client client, String imageUrlA, String imageUrlB) throws Exception;

}
