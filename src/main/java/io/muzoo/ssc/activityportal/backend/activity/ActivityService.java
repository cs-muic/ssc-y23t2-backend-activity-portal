package io.muzoo.ssc.activityportal.backend.activity;
import io.muzoo.ssc.activityportal.backend.SimpleResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ActivityService {
    SimpleResponseDTO editActivityDetails(Activity activityDetail, long groupId, long activityId);
}
