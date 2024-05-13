package id.ac.ui.cs.advprog.gametime.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
	private String password;
	private String confirmPassword;
	private String currentProfilePicture;
	private MultipartFile profilePicture;
	private String bio;
}
