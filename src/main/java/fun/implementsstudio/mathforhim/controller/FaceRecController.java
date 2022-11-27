package fun.implementsstudio.mathforhim.controller;

import com.aliyun.facebody20191230.models.CompareFaceResponse;
import fun.implementsstudio.mathforhim.service.IFaceRecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/v0/faceRec")
public class FaceRecController {

    @Autowired
    private IFaceRecService faceRecService;
    @Autowired
    private com.aliyun.facebody20191230.Client faceRecClient;

    @GetMapping("/recTest")
    public String recTest(){
        return "test1";
    }

//ok
//    @GetMapping("/createdb")
    public Integer createdb() throws Exception {
        return faceRecService.createFaceDb(faceRecClient, "mathforhim_ali_facerec_thedb");
    }
//ok
//    @GetMapping("/createFaceEntity")
    public Integer createFaceEntity() throws Exception {
        return faceRecService.addFaceEntity(faceRecClient, "mathforhim_ali_facerec_thedb","mathforhim_zhangjunyang0001");
    }
//ok
//    @GetMapping("/addFace")
    public Integer addFace() throws Exception {
        return faceRecService.addFace(faceRecClient,"mathforhim_ali_facerec_thedb","mathforhim_zhangqi0001",
                "https://mathforhim.oss-cn-shanghai.aliyuncs.com/zqmath4.png");
    }
    //ok
//@GetMapping("/compareFace")
    public CompareFaceResponse compareFace() throws Exception {
        return faceRecService.compareFace(faceRecClient,
                "https://mathforhim.oss-cn-shanghai.aliyuncs.com/zqmath4.png",
                "https://mathforhim.oss-cn-shanghai.aliyuncs.com/zqmath3.png");
    }

}
