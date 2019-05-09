package by.vit.mapping;

import by.vit.dto.request.CarRequestDTO;
import by.vit.dto.request.RoadRequestDTO;
import by.vit.dto.request.TaskDTO;
import by.vit.dto.request.UserRequestDTO;
import by.vit.dto.response.CarResponseDTO;
import by.vit.dto.response.RoadResponseDTO;
import by.vit.dto.response.SolutionResponseDTO;
import by.vit.dto.response.UserResponseDTO;
import by.vit.model.*;
import by.vit.model.solution.Solution;

public interface Mapping {
    /**
     * maps the taskDTO to the array of points
     *
     * @param taskDTO it's list of points and list of orders (mass of cargo, that we need to deliver to the point)
     * @return
     */
    Point[] mapTaskToPoint(TaskDTO taskDTO);

    Road mapRoadRequestDTOToRoad(RoadRequestDTO roadRequestDTO);

    RoadResponseDTO mapRoadToRoadResponseDTO(Road road);

    Car mapCarRequestDTOToCar(CarRequestDTO carRequestDTO);

    CarResponseDTO mapCarToCarResponseDTO(Car car);

    /**
     * maps the Solution to the SolutionDTO
     *
     * @param solution
     * @return SolutionResponseDTO
     */
    SolutionResponseDTO mapSolutionToSolutionResponseDTO(Solution solution);

    /**
     * @param id1 road1Id
     * @param id2 road1Id
     * @return RoadId
     */
    RoadId mapToRoadId(Long id1, Long id2);

    /**
     * maps the UserRequestDTO to the User
     *
     * @param userRequestDTO
     * @return User
     */
    User mapUserRequestDTOTOUser(UserRequestDTO userRequestDTO);

    /**
     * maps the User to the UserResponseDTO
     *
     * @param user
     * @return UserResponseDTO
     */
    UserResponseDTO mapUserTOUserResponseDTO(User user);
}
