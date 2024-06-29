package likelion.ideateam3.happy_board.controller.file;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import likelion.ideateam3.happy_board.domain.member.MemberPrincipal;
import likelion.ideateam3.happy_board.service.file.AwsS3Service;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/files")
public class FileController {

	private final AwsS3Service awsS3Service;

	@PostMapping("/upload")
	public String uploadFile(
		@RequestPart(value = "file") MultipartFile multipartFile) {
		return awsS3Service.uploadFile(multipartFile);
	}
}
