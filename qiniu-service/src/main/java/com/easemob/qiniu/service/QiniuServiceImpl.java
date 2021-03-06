package com.easemob.qiniu.service;

import com.easemob.qiniu.config.QiniuProperties;
import com.easemob.qiniu.enums.PlayProtocol;
import com.easemob.qiniu.pili.Client;
import org.springframework.stereotype.Component;

/**
 * @author shenchong@easemob.com 2020/5/11
 */
@Component
public class QiniuServiceImpl implements IQiniuService {

    private final Client client;
    private final String publishDomain;
    private final String rtmpDomain;
    private final String hlsDomain;
    private final String hdlDomain;
    private final String hub;
    private final int expire;

    public QiniuServiceImpl(QiniuProperties properties) {

        this.client = new Client(properties.getAccessKey(), properties.getSecretKey());
        this.publishDomain = properties.getPublishDomain();
        this.rtmpDomain = properties.getRtmpDomain();
        this.hlsDomain = properties.getHlsDomain();
        this.hdlDomain = properties.getHdlDomain();
        this.hub = properties.getHub();
        this.expire = properties.getExpire();
    }

    @Override
    public String RTMPPublishURL(String domain, String hub, String streamKey, Integer expire) {

        domain = domain == null ? this.publishDomain : domain;
        hub = hub == null ? this.hub : hub;
        expire = expire == null ? this.expire : expire;

        return client.RTMPPublishURL(domain, hub, streamKey, expire);
    }

    @Override
    public String playURL(String protocol, String domain, String hub, String streamKey) {

        hub = hub == null ? this.hub : hub;

        PlayProtocol playProtocol;

        try {
            playProtocol = PlayProtocol.valueOf(protocol.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unsupported protocol");
        }

        switch (playProtocol) {

            case RTMP:
                domain = domain == null ? this.rtmpDomain : domain;
                return client.RTMPPlayURL(domain, hub, streamKey);

            case HLS:
                domain = domain == null ? this.hlsDomain : domain;
                return client.HLSPlayURL(domain, hub, streamKey);

            case HDL:
                domain = domain == null ? this.hdlDomain : domain;
                return client.HDLPlayURL(domain, hub, streamKey);

            default:
                throw new IllegalArgumentException("Unsupported protocol");
        }
    }
}
