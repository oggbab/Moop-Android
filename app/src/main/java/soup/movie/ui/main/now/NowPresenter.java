package soup.movie.ui.main.now;

import com.jakewharton.rxrelay2.BehaviorRelay;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import soup.movie.data.MoobRepository;
import soup.movie.data.request.NowMovieRequest;
import soup.movie.data.response.NowMovieResponse;
import soup.movie.di.scope.FragmentScoped;
import soup.movie.ui.BasePresenter;

@FragmentScoped
public class NowPresenter extends BasePresenter<NowContract.View>
        implements NowContract.Presenter {

    private final MoobRepository moobRepository;

    private BehaviorRelay<Boolean> refreshRelay = BehaviorRelay.createDefault(true);

    @Inject
    NowPresenter(MoobRepository moobRepository) {
        this.moobRepository = moobRepository;
    }

    @Override
    protected void initObservable(CompositeDisposable subscriptions) {
        subscriptions.add(getViewStateObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::render));
    }

    private Flowable<NowViewState> getViewStateObservable() {
        return refreshRelay.toFlowable(BackpressureStrategy.LATEST)
                .flatMap(ignore -> moobRepository.getNowList(NowMovieRequest.INSTANCE)
                        .map(NowMovieResponse::getList)
                        .map(NowViewState.DoneState::new)
                        .toFlowable());
    }

    @Override
    public void refresh() {
        refreshRelay.accept(true);
    }
}
