package uz.pdp.warehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.pdp.warehouse.common.Result;
import uz.pdp.warehouse.entity.Attachment;
import uz.pdp.warehouse.repository.AttachmentContentRepository;
import uz.pdp.warehouse.service.AttachmentService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/attachment")
public class AttachmentController {

    @Autowired
    AttachmentService attachmentService;

    @PostMapping("/post")
    public Result uploadFile(MultipartHttpServletRequest request) throws IOException {
        return attachmentService.post(request);
    }

    @GetMapping("/info")
    public Page<Attachment> getInfo(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return attachmentService.findAll(pageable);
    }

}
