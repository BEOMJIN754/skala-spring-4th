package com.onlineshop.shop1.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onlineshop.shop1.auth.dto.LoginRequest;
import com.onlineshop.shop1.auth.dto.LoginResponse;
import com.onlineshop.shop1.auth.dto.SignupRequest;
import com.onlineshop.shop1.auth.dto.SignupResponse;
import com.onlineshop.shop1.domain.customer.entity.Customer;
import com.onlineshop.shop1.domain.customer.repository.CustomerRepository;
import com.onlineshop.shop1.exception.DuplicateCustomerException;
import com.onlineshop.shop1.exception.LoginFailedException;
import com.onlineshop.shop1.security.jwt.JwtTokenProvider;

import com.onlineshop.shop1.auth.dto.ReissueRequest;
import com.onlineshop.shop1.auth.dto.ReissueResponse;
import com.onlineshop.shop1.exception.InvalidRefreshTokenException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;

    @Transactional
    public SignupResponse signup(SignupRequest request) {

        validateDuplicateCustomerId(request.getCustomerId());

        String encodedPassword = passwordEncoder.encode(request.getCustomerPw());

        Customer customer = Customer.createUser(
                request.getCustomerId(), encodedPassword);

        Customer savedCustomer = customerRepository.save(customer);

        return SignupResponse.from(savedCustomer);
    }

    private void validateDuplicateCustomerId(String customerId) {
        if (customerRepository.existsByCustomerId(customerId)) {
            throw new DuplicateCustomerException();
        }
    }

    public LoginResponse login(LoginRequest request) {

        Customer customer = customerRepository
                .findByCustomerId(request.getCustomerId())
                .orElseThrow(LoginFailedException::new);

        validatePassword(
                request.getCustomerPw(),
                customer.getEncodedPassword());

        String accessToken = jwtTokenProvider.createAccessToken(
                customer.getCustomerId(),
                customer.getRole());

        String refreshToken = jwtTokenProvider.createRefreshToken(
                customer.getCustomerId());

        refreshTokenService.save(
                customer.getCustomerId(),
                refreshToken,
                jwtTokenProvider.getRefreshTokenExpiration());

        return LoginResponse.of(
                accessToken,
                refreshToken,
                jwtTokenProvider.getAccessTokenExpiration(),
                jwtTokenProvider.getRefreshTokenExpiration());
    }

    private void validatePassword(
            String rawPassword,
            String encodedPassword) {
        // encode().equals 를 사용하지 않는 이유는 Bcrypt는 같은 비밀번호를 다시 암호화해도 결과가 달라질 수 있기에
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new LoginFailedException();
        }
    }

    public ReissueResponse reissue(ReissueRequest request) {

    String oldRefreshToken = request.getRefreshToken();

    validateRefreshToken(oldRefreshToken);

    String customerId =
            jwtTokenProvider.getCustomerId(oldRefreshToken);

    if (!refreshTokenService.matches(
            customerId,
            oldRefreshToken
    )) {
        throw new InvalidRefreshTokenException();
    }

    Customer customer = customerRepository
            .findByCustomerId(customerId)
            .orElseThrow(InvalidRefreshTokenException::new);

    String newAccessToken =
            jwtTokenProvider.createAccessToken(
                    customer.getCustomerId(),
                    customer.getRole()
            );

    String newRefreshToken =
            jwtTokenProvider.createRefreshToken(
                    customer.getCustomerId()
            );

    refreshTokenService.save(
            customer.getCustomerId(),
            newRefreshToken,
            jwtTokenProvider.getRefreshTokenExpiration()
    );

    return ReissueResponse.of(
            newAccessToken,
            newRefreshToken,
            jwtTokenProvider.getAccessTokenExpiration(),
            jwtTokenProvider.getRefreshTokenExpiration()
    );
}

    private void validateRefreshToken(String refreshToken) {

        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new InvalidRefreshTokenException();
        }

        if (!jwtTokenProvider.isRefreshToken(refreshToken)) {
            throw new InvalidRefreshTokenException();
        }
    }
}
