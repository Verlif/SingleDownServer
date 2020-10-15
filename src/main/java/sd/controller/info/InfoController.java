package sd.controller.info;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sd.model.result.Result;
import sd.model.result.SusResult;

@RestController
@RequestMapping("server")
public class InfoController {

    @RequestMapping("/info")
    public Result serverInfo() {
        Result result = new SusResult();
        result.addObject("version", "Beta0.8");
        return result;
    }
}
