package molek_school.mapper;


import molek_school.dto.request.RegisterRequest;
import molek_school.dto.response.UserResponse;
import molek_school.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    User toUserEntity(RegisterRequest registerRequest);


    UserResponse toUserResponse(User savedUser);
}
