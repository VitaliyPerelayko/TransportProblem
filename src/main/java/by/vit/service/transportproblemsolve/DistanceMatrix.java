package by.vit.service.transportproblemsolve;

import com.programmerare.shortestpaths.core.api.Path;
import org.springframework.stereotype.Service;

import java.util.List;


public interface DistanceMatrix {

    Double[][] getDistanceMatrix();


    List<Path> getPathList();

    Long[] getPointsId();
}
