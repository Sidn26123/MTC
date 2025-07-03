package com.sidn.metruyenchu.identity_service.service;

import com.sidn.metruyenchu.identity_service.dto.request.RoleRequest;
import com.sidn.metruyenchu.identity_service.dto.response.RoleResponse;
import com.sidn.metruyenchu.identity_service.mapper.RoleMapper;
import com.sidn.metruyenchu.identity_service.repository.PermissionRepository;
import com.sidn.metruyenchu.identity_service.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@EnableMethodSecurity
public class RoleService {
    RoleRepository roleRepository;
    RoleMapper roleMapper;
    PermissionRepository permissionRepository;

   public RoleResponse create(RoleRequest request){
       var role = roleMapper.toRole(request);

       var permissions = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));
        return roleMapper.toRoleResponse(roleRepository
                .save(role));
   }

   public List<RoleResponse> getAll(){
        return roleRepository.findAll()
                .stream()
                .map(roleMapper::toRoleResponse)
                .toList();
   }

   public void deleteById(String role){
       roleRepository.deleteById(role);
   }
}
