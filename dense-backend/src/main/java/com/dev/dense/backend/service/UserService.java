/*
 * Copyright (C)
 *
 * @author Rojar Smith
 * @date 2023-10-03
 */
package com.dev.dense.backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dev.dense.backend.domain.Member;
import com.dev.dense.backend.domain.Permission;
import com.dev.dense.backend.domain.Role;
import com.dev.dense.backend.repository.MemberRepository;
import com.dev.dense.backend.repository.PermissionRepository;

@Service
public class UserService {

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	PermissionRepository permissionRepository;

	public boolean createPermission(Permission permission) {
		boolean existence = permissionRepository.existsById(permission.getId());

		if (existence) {
			return false;
		}

		Permission saved = permissionRepository.save(permission);
		if (saved != permission) {
			return false;
		}
		return true;
	}

	public Optional<Permission> readPermission(Long id) {
		return permissionRepository.findById(id);
	}

	public Optional<Permission> readPermission(String name) {
		return permissionRepository.findByName(name);
	}

	public List<Permission> readPermissions() {
		return permissionRepository.findAll();
	}

	public List<Permission> readPermissions(Long userId) {
		List<Permission> permissions = new ArrayList<Permission>();
		Optional<Member> member = memberRepository.findById(userId);
		if (member.isEmpty()) {
			return permissions;
		}

		for (Role role : member.get().getRoles()) {
			for (Permission permission : role.getPermissions()) {
				if (!permissions.contains(permission)) {
					permissions.add(permission);
				}
			}
		}

		return permissions;
	}

	public boolean updatePermission(Permission permission) {
		Permission permissionReturn = permissionRepository.save(permission);
		if (permissionReturn != permission) {
			return false;
		}
		return true;
	}

	public boolean updatePermissions(List<Permission> permissions) {
		List<Permission> list = permissionRepository.saveAll(permissions);
		if (list.size() != permissions.size()) {
			return false;
		}
		return true;
	}

	public void deletePermission(Long id) {
		permissionRepository.deleteById(id);
	}

}
