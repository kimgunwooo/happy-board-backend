package likelion.ideateam3.happy_board.service.board;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "aws")
public class AwsS3Properties {
	private String accessKey;
	private String secretKey;
	private S3 s3;

	@Getter
	@Setter
	public static class S3 {
		private String bucket;
	}
}
