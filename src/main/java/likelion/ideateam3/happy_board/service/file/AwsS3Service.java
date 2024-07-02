package likelion.ideateam3.happy_board.service.file;

import static likelion.ideateam3.happy_board.response.exception.ExceptionType.*;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import likelion.ideateam3.happy_board.response.exception.BusinessException;
import likelion.ideateam3.happy_board.service.board.AwsS3Properties;
import likelion.ideateam3.happy_board.utils.CommonUtils;
import lombok.RequiredArgsConstructor;

@EnableConfigurationProperties(AwsS3Properties.class)
@RequiredArgsConstructor
@Service
public class AwsS3Service {
	private final AmazonS3 amazonS3Client;
	private final AwsS3Properties awsS3Properties;

	public String uploadFile(MultipartFile multipartFile) {
		this.validateFileExists(multipartFile);

		String fileName = CommonUtils.buildFileName(multipartFile.getOriginalFilename());

		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType(multipartFile.getContentType());

		try (InputStream inputStream = multipartFile.getInputStream()) {
			amazonS3Client.putObject(new PutObjectRequest(awsS3Properties.getS3().getBucket(), fileName, inputStream, objectMetadata)
				.withCannedAcl(CannedAccessControlList.PublicRead));
		} catch (IOException e) {
			throw new BusinessException(FAIL_FILE_UPLOAD);
		}

		return amazonS3Client.getUrl(awsS3Properties.getS3().getBucket(), fileName).toString();
	}

	private void validateFileExists(MultipartFile multipartFile) {
		if (multipartFile.isEmpty()) {
			throw new BusinessException(FILE_NOT_FOUND);
		}
		if (multipartFile.getOriginalFilename() == null) {
			throw new BusinessException(FILENAME_NOT_FOUND);
		}
	}
}
