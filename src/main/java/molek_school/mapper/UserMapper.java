package molek_school.mapper;

import com.jumong.E.TMotors.dto.request.RegisterRequest;
import com.jumong.E.TMotors.dto.response.UserResponse;
import com.jumong.E.TMotors.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    User toUserEntity(RegisterRequest registerRequest);


    UserResponse toUserResponse(User savedUser);
}
