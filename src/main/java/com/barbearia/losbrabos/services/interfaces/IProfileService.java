package com.barbearia.losbrabos.services.interfaces;

import com.barbearia.losbrabos.domain.user.ProfileDTO;
import com.barbearia.losbrabos.domain.user.ProfileResponseDTO;

public interface IProfileService {
    ProfileResponseDTO update(ProfileDTO data);
}
